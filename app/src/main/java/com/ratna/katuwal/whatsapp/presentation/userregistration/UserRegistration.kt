package com.ratna.katuwal.whatsapp.presentation.userregistration

import android.app.Activity
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ratna.katuwal.whatsapp.R
import com.ratna.katuwal.whatsapp.presentation.navigations.Routes
import com.ratna.katuwal.whatsapp.presentation.viewmodel.AuthState
import com.ratna.katuwal.whatsapp.presentation.viewmodel.PhoneAuthViewModel

@Composable

fun UserRegistration(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    phoneAuthViewModel: PhoneAuthViewModel = hiltViewModel()
) {
    val authState by phoneAuthViewModel.authState.collectAsState()
    val context = LocalContext.current
    val activity = LocalContext.current as Activity


    var expanded by remember {
        mutableStateOf(false)
    }

    var selectedCountry by remember {
        mutableStateOf("Nepal")
    }

    var countryCode by remember {
        mutableStateOf("+977")
    }

    var phoneNumber by remember {
        mutableStateOf("")
    }

    var otp by remember {
        mutableStateOf("")
    }

    var verificationId by remember {
        mutableStateOf<String?>(null)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()
            .padding(16.dp)
            .background(color = colorResource(R.color.white)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            "Enter Your Phone Number",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = colorResource(R.color.dark_green)
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row() {
            Text(
                text = buildAnnotatedString {
                    append("WhatsApp will need to verify your phone number. ")
                    pushStyle(
                        SpanStyle(color = colorResource(R.color.light_green))
                    )
                    append("What's my number?")
                },
                color = Color.Black,
                textAlign = TextAlign.Center,
                lineHeight = 22.sp,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {

            Column(horizontalAlignment = Alignment.CenterHorizontally) {

                TextButton(
                    onClick = { expanded = true },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Box(modifier = Modifier.width(230.dp)) {

                        Text(
                            text = selectedCountry,
                            modifier = Modifier.align(Alignment.Center),
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = colorResource(R.color.black)
                        )

                        Icon(
                            imageVector = Icons.Default.ArrowDropDown,
                            contentDescription = null,
                            modifier = Modifier.align(Alignment.CenterEnd),
                            tint = colorResource(R.color.light_green)
                        )
                    }
                }

                HorizontalDivider(
                    modifier = Modifier.padding(horizontal = 66.dp),
                    thickness = 2.dp,
                    color = colorResource(R.color.light_green)
                )
            }

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                listOf("Nepal", "India", "USA", "China").forEach { country ->
                    DropdownMenuItem(
                        text = { Text(text = country) },
                        onClick = {
                            selectedCountry = country
                            expanded = false
                        }
                    )
                }
            }
        }

//        Column(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            horizontalAlignment = Alignment.CenterHorizontally
//        ) {
//            Row() {
//                TextField(
//                    countryCode, onValueChange = {
//                        countryCode = it
//                    }, modifier = Modifier.width(80.dp),
//                    singleLine = true,
//                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
//                    colors = TextFieldDefaults.colors(
//                        unfocusedContainerColor = Color.Transparent,
//                        focusedContainerColor = Color.Transparent,
//                        unfocusedIndicatorColor = colorResource(R.color.light_green),
//                        focusedIndicatorColor = colorResource(R.color.light_green)
//                    )
//                )
//
//                Spacer(modifier = Modifier.width(6.dp))
//
//                TextField(
//                    phoneNumber,
//                    onValueChange = {
//                        phoneNumber = it
//                    },
//                    placeholder = { Text(text = "Phone Number") },
//                    singleLine = true,
//                    textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
//                    colors = TextFieldDefaults.colors(
//                        unfocusedContainerColor = Color.Transparent,
//                        focusedContainerColor = Color.Transparent,
//                        unfocusedIndicatorColor = colorResource(R.color.light_green),
//                        focusedIndicatorColor = colorResource(R.color.light_green)
//                    )
//                )
//            }
//
//            Spacer(modifier = Modifier.height(30.dp))
//
//            Text(
//                "Carrier charges may apply",
//                color = Color.Gray.copy(alpha = 0.6f),
//                fontSize = 16.sp
//            )
//
//            Spacer(modifier = Modifier.height(30.dp))
//
//            Button(
//                onClick = {}, modifier = Modifier.size(height = 50.dp, width = 150.dp),
//                shape = RoundedCornerShape(8.dp),
//                colors = ButtonDefaults.buttonColors(colorResource(R.color.dark_green))
//            ) {
//                Text(
//                    "Next",
//                    color = colorResource(R.color.white),
//                    fontWeight = FontWeight.Bold,
//                    fontSize = 18.sp
//                )
//            }
//        }

        when (authState) {
            is AuthState.Ideal, is AuthState.Loading, is AuthState.CodeSent -> {
                if (authState is AuthState.CodeSent) {
                    verificationId = (authState as AuthState.CodeSent).verificationId
                }
                if (verificationId == null) {
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        TextField(
                            countryCode, onValueChange = {
                                countryCode = it
                            }, modifier = Modifier.width(80.dp),
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
                                // unfocusedIndicatorColor = colorResource(R.color.light_green),
                                focusedIndicatorColor = colorResource(R.color.light_green)
                            )
                        )

                        Spacer(modifier = Modifier.width(6.dp))
                        TextField(
                            phoneNumber,
                            onValueChange = {
                                phoneNumber = it
                            },
                            placeholder = { Text(text = "Phone Number") },
                            singleLine = true,
                            textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = Color.Transparent,
                                focusedContainerColor = Color.Transparent,
//                                unfocusedIndicatorColor = colorResource(R.color.light_green),
                                focusedIndicatorColor = colorResource(R.color.light_green)
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (phoneNumber.isNotEmpty()) {
                                val fullPhoneNumber = "$countryCode$phoneNumber"
                                phoneAuthViewModel.sendVerificationCode(fullPhoneNumber, activity)
                            } else {
                                Toast.makeText(
                                    context,
                                    "please enter a valid phone number",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }, modifier = Modifier.size(height = 50.dp, width = 150.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.dark_green))
                    ) {
                        Text(
                            "Sent OTP",
                            color = colorResource(R.color.white),
                            fontWeight = FontWeight.Bold,
                            fontSize = 18.sp
                        )
                    }

                    if (authState is AuthState.Loading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }
                } else {
                    Spacer(modifier = Modifier.height(40.dp))

                    Text(
                        "Enter OTP",
                        color = colorResource(R.color.dark_green),
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    TextField(
                        otp,
                        onValueChange = {
                            otp = it
                        },
                        placeholder = { Text(text = "OTP") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        textStyle = LocalTextStyle.current.copy(fontSize = 18.sp),
                        colors = TextFieldDefaults.colors(
                            unfocusedContainerColor = Color.Transparent,
                            focusedContainerColor = Color.Transparent,
//                                unfocusedIndicatorColor = colorResource(R.color.light_green),
                            focusedIndicatorColor = Color.Transparent
                        )
                    )

                    Spacer(modifier = Modifier.height(32.dp))

                    Button(
                        onClick = {
                            if (otp.isNotEmpty() && verificationId != null) {
                                phoneAuthViewModel.verifyCode(otp, context)
                            } else {
                                Toast.makeText(
                                    context,
                                    "please enter a valid OTP",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        },
                        shape = RoundedCornerShape(6.dp),
                        colors = ButtonDefaults.buttonColors(colorResource(R.color.dark_green))
                    ) {
                        Text(
                            "Verify OTP",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    if (authState is AuthState.Loading) {
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator()
                    }
                }
            }

            is AuthState.Success -> {
                Log.d("PhoneAuth", "Login Successful")
                phoneAuthViewModel.resetAuthState()

                navHostController.navigate(Routes.UserProfileScreen) {
                    popUpTo<Routes.UserRegistrationScreen> {
                        inclusive = true
                    }
                }
            }

            is AuthState.Error -> {
                Toast.makeText(context, (authState as AuthState.Error).message, Toast.LENGTH_SHORT)
                    .show()

            }
        }
    }
}