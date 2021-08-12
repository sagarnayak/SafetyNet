package com.sagar.safetynet.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Base64
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.safetynet.SafetyNet
import com.google.android.gms.safetynet.SafetyNetApi
import com.google.gson.Gson
import com.nimbusds.jose.JWSObject
import com.sagar.android.logutilmaster.LogUtil
import com.sagar.safetynet.databinding.ActivityMainBinding
import com.sagar.safetynet.models.AuthenticationRequest
import com.sagar.safetynet.models.Response
import dagger.hilt.android.AndroidEntryPoint
import java.nio.charset.StandardCharsets
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    private lateinit var logUtil: LogUtil

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(LayoutInflater.from(this))

        setContentView(binding.root)

        bindToViewModel()

        Handler(Looper.getMainLooper()).postDelayed(
            {
                authenticate()
            },
            3000
        )
    }

    private fun bindToViewModel() {
    }

    private fun authenticate() {
        viewModel.authenticate(
            AuthenticationRequest(
                userEmail = "sagar@gmail.com",
                password = "admin"
            )
        )
    }

    private fun getPlatformJwt() {
        if (GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this)
            == ConnectionResult.SUCCESS
        ) {
            val client = SafetyNet.getClient(this)
            val nounce = "Sagar".toByteArray()
            logUtil.logV("Key >> ${Base64.encodeToString(nounce, Base64.DEFAULT)}")
            client.attest(nounce, "AIzaSyCmJeN7rfeIYtuL-J-_PMlP8dvTkNL2NEg")
                .addOnSuccessListener {
                    val response: SafetyNetApi.AttestationResponse = it
                    logUtil.logV("response >> ${response.jwsResult}")
                    val jws: JWSObject = JWSObject.parse(response.jwsResult)
                    logUtil.logI(
                        """
                        result :
                        >>Header
                        ${jws.header}
                    """.trimIndent()
                    )
                    logUtil.logI(
                        """
                        result :
                        >>509
                        ${jws.header.x509CertChain}
                    """.trimIndent()
                    )
                    logUtil.logI(
                        """
                        result :
                        >>payload
                        ${jws.payload.toJSONObject()}
                    """.trimIndent()
                    )
                    logUtil.logI(
                        """
                        result :
                        >>signature
                        ${jws.signature}
                    """.trimIndent()
                    )
                    logUtil.logI(
                        """
                        result :
                        >>signature string
                        ${jws.signature.decodeToString()}
                    """.trimIndent()
                    )
                    val responseJws = parseJsonWebSignature(response.jwsResult)
                    logUtil.logI(
                        """
                        Base 64 decode :
                        $responseJws
                    """.trimIndent()
                    )
                    val decode =
                        String(
                            Base64.decode(
                                responseJws!!.apkCertificateDigestSha256!![0],
                                Base64.DEFAULT
                            ),
                            StandardCharsets.UTF_8
                        )
                    logUtil.logI(
                        """
                        Base 64 decode of apkCertificateDigestSha256 :
                        $decode
                    """.trimIndent()
                    )
                }
                .addOnFailureListener {
                    logUtil.logV("error >> ${it}")
                }
        } else {
            logUtil.logV("No play service installed")
        }
    }

    private fun parseJsonWebSignature(jwsResult: String?): Response? {
        jwsResult ?: return null
        val parts = jwsResult.split(".")
        return if (parts.size == 3) {
            //we're only really interested in the body/payload
            val decodedPayload = String(Base64.decode(parts[1], Base64.DEFAULT))
            Gson().fromJson(decodedPayload, Response::class.java)
        } else {
            null
        }
    }
}