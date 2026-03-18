package com.ratna.katuwal.whatsapp.presentation.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.util.TimeUtils
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.database.FirebaseDatabase
import com.ratna.katuwal.whatsapp.model.PhoneAuthUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import androidx.core.content.edit
import com.google.firebase.database.getValue


@HiltViewModel
class PhoneAuthViewModel @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDatabase: FirebaseDatabase
) : ViewModel() {

    private val _authState = MutableStateFlow<AuthState>(AuthState.Ideal)
    val authState = _authState.asStateFlow()

    private val userRef = firebaseDatabase.reference.child("users")

    fun sendVerificationCode(phoneNumber: String, activity: Activity) {
        _authState.value = AuthState.Loading
        val option = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(id: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(id, token)
                Log.d("PhoneAuth", "onCodeSent triggered. Verification ID: $id")
                _authState.value = AuthState.CodeSent(id)
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                signWithCredential(credential, context = activity)
            }

            private fun signWithCredential(
                credential: PhoneAuthCredential,
                context: Context
            ) {
               _authState.value = AuthState.Loading
                firebaseAuth.signInWithCredential(credential)
                    .addOnCompleteListener{ task ->
                        if (task.isSuccessful) {
                            val user = firebaseAuth.currentUser
                            val phoneAuthUser = PhoneAuthUser(
                                userId = user?.uid ?: "",
                                phoneNumber = user?.phoneNumber ?: "",
                            )

                            markUserAsSignIn(context)
                            _authState.value = AuthState.Success(phoneAuthUser)

                            fetchUserProfile(user?.uid ?: "")
                        } else {
                            _authState.value = AuthState.Error(task.exception?.message ?: "Sign-in failed")
                        }
                    }
            }

           private fun fetchUserProfile(userId: String) {
               val userRef = userRef.child(userId)
               userRef.get().addOnSuccessListener { snapshot ->
                   if (snapshot.exists()) {
                       val userProfile = snapshot.getValue(PhoneAuthUser:: class.java)
                       if (userProfile != null) {
                           _authState.value = AuthState.Success(userProfile)
                       }
                   }
               }.addOnFailureListener {
                   _authState.value = AuthState.Error("Failed to fetch user profile")
               }
           }

            fun verifyCode(otp: String, context: Context) {
                val currentAuthState = _authState.value
                if (currentAuthState !is AuthState.CodeSent || currentAuthState.verificationId.isEmpty()) {
                    Log.e("PhoneAuth", "Invalid verification ID")
                    _authState.value = AuthState.Error("Invalid verification ID")
                    return
                }
                val credential = PhoneAuthProvider.getCredential(currentAuthState.verificationId, otp)
                signWithCredential(credential, context)

            }

            private fun markUserAsSignIn(context: Context) {
                val sharedPreferences = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
                sharedPreferences.edit { putBoolean("is_sign_in", true) }
            }


            override fun onVerificationFailed(exception: FirebaseException) {
                Log.e("PhoneAuth", "Verification failed: ${exception.message}")
                _authState.value = AuthState.Error(exception.message ?: "Verification failed")

            }

        }

        val phoneAuthOptions = PhoneAuthOptions.newBuilder(firebaseAuth)
            .setPhoneNumber(phoneNumber)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity)
            .setCallbacks(option)
            .build()

        PhoneAuthProvider.verifyPhoneNumber(phoneAuthOptions)
    }

}

sealed class AuthState {
    object Ideal : AuthState()
    object Loading : AuthState()

    data class CodeSent(val verificationId: String) : AuthState()
    data class Success(val user: PhoneAuthUser) : AuthState()
    data class Error(val message: String) : AuthState()
}