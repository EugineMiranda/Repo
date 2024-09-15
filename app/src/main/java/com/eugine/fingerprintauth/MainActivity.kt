package com.eugine.fingerprintauth

import androidx.biometric.BiometricPrompt
import androidx.core.content.ContextCompat
import android.webkit.WebView
import android.webkit.WebViewClient
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.webkit.JavascriptInterface
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricManager.Authenticators.BIOMETRIC_STRONG
import androidx.biometric.BiometricManager.Authenticators.DEVICE_CREDENTIAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.eugine.fingerprintauth.BiometricPromptManager.*
import com.eugine.fingerprintauth.ui.theme.FingerPrintAuthTheme

class MainActivity : AppCompatActivity() {

    private val promptManager by lazy {
        BiometricPromptManager(this
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val myWebView: WebView = findViewById(R.id.webView)
        myWebView.webViewClient = WebViewClient()
        myWebView.settings.javaScriptEnabled = true
        myWebView.settings.domStorageEnabled = true

        // Add JavaScript interface to handle fingerprint authentication
        myWebView.addJavascriptInterface(WebAppInterface(this), "Android")

        // Load your register or login page
        myWebView.loadUrl("https://wzp525fg-8080.asse.devtunnels.ms/register.html")

        enableEdgeToEdge()

        val executor = ContextCompat.getMainExecutor(this)
        val biometricPrompt = BiometricPrompt(this, executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(
                    result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    // Fingerprint authenticated successfully
                    // Redirect to joinQueue.html
                    val myWebView: WebView = findViewById(R.id.webView)
                    myWebView.loadUrl("http://localhost:8080/joinQueue.html")
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Authentication failed
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Authentication")
            .setSubtitle("Use your fingerprint to log in")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)

        enableEdgeToEdge()
        setContent {
            FingerPrintAuthTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val biometricResult by promptManager.promptResults.collectAsState(
                        initial = null
                    )
                    val enrollLauncher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.StartActivityForResult(),
                        onResult = {
                            println("Activity results: $it")
                        }
                    )
                    LaunchedEffect(biometricResult) {
                        if(biometricResult is BiometricResult.AuthenticationNotSet) {
                            if(Build.VERSION.SDK_INT >= 30) {
                                val enrollIntent = Intent(Settings.ACTION_BIOMETRIC_ENROLL).apply {
                                    putExtra(
                                    Settings.EXTRA_BIOMETRIC_AUTHENTICATORS_ALLOWED,
                                    BIOMETRIC_STRONG or DEVICE_CREDENTIAL
                                    )
                                }
                                enrollLauncher.launch(enrollIntent)
                            }
                        }
                    }
                    Column(
                        modifier = Modifier
                            .fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Button(onClick = {
                            promptManager.showBiometricPrompt(
                                title = "Authenticate",
                                description = "Use your fingerprint to authenticate"
                            )
                        }) {
                            Text(text = "Authenticate")
                        }
                        biometricResult?.let { result ->
                            when(result) {
                                    is BiometricResult.AuthenticationSuccess -> {
                                        // Authentication successful, now load login.html in the WebView
                                        myWebView.loadUrl("https://wzp525fg-8080.asse.devtunnels.ms/login.html")
                                        Text("Authentication Success")
                                    }
                                    is BiometricResult.AuthenticationError -> {
                                        Text(text = result.error)
                                    }
                                    BiometricResult.AuthenticationFailed -> {
                                        Text (text = "Authentication failed")
                                    }
                                    BiometricResult.AuthenticationNotSet -> {
                                        Text(text = "Authentication not set")
                                    }
                                    BiometricResult.FeatureUnavailable -> {
                                        Text (text = "Feature unavailable")
                                    }
                                    BiometricResult.HardwareUnavailable -> {
                                        Text (text = "Hardware unavailable")
                                    }
                                }
                            }
                        }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    FingerPrintAuthTheme {
        Greeting("Android")
    }
}

class WebAppInterface(private val activity: AppCompatActivity) {
    @JavascriptInterface
    fun registerFingerprint() {
        val biometricPrompt = BiometricPrompt(activity, ContextCompat.getMainExecutor(activity),
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    activity.runOnUiThread {
                        val webView = activity.findViewById<WebView>(R.id.webView)
                        webView.evaluateJavascript(
                            "onFingerprintRegistrationSuccess();",
                            null
                        )
                    }
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                    // Notify the JavaScript side of failure
                    activity.runOnUiThread {
                        val webView = activity.findViewById<WebView>(R.id.webView)
                        webView.evaluateJavascript(
                            "onFingerprintRegistrationFailure();",
                            null
                        )
                    }
                }
            })

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Fingerprint Registration")
            .setSubtitle("Use your fingerprint to register")
            .setNegativeButtonText("Cancel")
            .build()

        biometricPrompt.authenticate(promptInfo)
    }
}
