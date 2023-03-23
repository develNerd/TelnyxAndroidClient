package org.telnyx.androidclient.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.fragment.findNavController
import org.koin.androidx.viewmodel.ext.android.activityViewModel
import org.telnyx.androidclient.R
import org.telnyx.androidclient.ui.core.BaseComposeFragment
import org.telnyx.androidclient.ui.theme.Colors
import org.telnyx.androidclient.ui.theme.Dimens
import org.telnyx.androidclient.ui.viewcomponets.*


class LoginScreen : BaseComposeFragment() {

    private val viewModel by activityViewModel<MainViewModel>()

    @Composable
    @Preview
    override fun InitComposeUI() {

        val uiState = viewModel.uiStatus.collectAsState(MainViewModel.UiState.ConnectionResult()).value
        var clientConnected by remember {
            mutableStateOf(false)
        }

        var loading by remember {
            mutableStateOf(false)
        }

        var connectionStatus by remember {
            mutableStateOf("")
        }

        when(uiState){
            is MainViewModel.UiState.ConnectionResult -> {
                when (uiState.status) {
                    MainViewModel.ConnectionStatus.LOADING -> {
                        connectionStatus =  stringResource(id = R.string.not_connected)
                    }
                    MainViewModel.ConnectionStatus.ERROR -> {
                        connectionStatus =  uiState.error ?: ""
                        loading = false
                    }
                    MainViewModel.ConnectionStatus.CONNECTED_AND_LOGGED_IN -> {
                        viewModel.saveCredential()
                        findNavController().navigate(R.id.action_loginScreen_to_homeFragment)
                    }
                    else -> {
                        connectionStatus = stringResource(id = R.string.connected)
                    }
                }
                clientConnected =  uiState.status == MainViewModel.ConnectionStatus.CONNECTED
            }
            else -> {}
        }

        ColumnInset(verticalArrangement = Arrangement.spacedBy(Dimens.largePadding),
            bottomBar = {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .padding(Dimens.largePadding), contentAlignment = Alignment.Center) {
                    MediumTextBold(text =  connectionStatus, color = Colors.colorWarning)
                    MediumTextBold(text = "")
                }
            }) {
            MainHeader(false)
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                val welcomeText = buildAnnotatedString {
                    withStyle(SpanStyle(color = Colors.colorPrimary, fontWeight = FontWeight.Bold)){
                        append(stringResource(id = R.string.welcome_ex))
                    }
                    append(" ")
                    append(stringResource(id = R.string.login_with_credentials))
                }
                RegularText(text = welcomeText)
            }
            /**@UserNameAndPassword*/
            val (userName,setUserName) = remember { mutableStateOf("") }
            val (password,setPassword) = remember { mutableStateOf("") }
            Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(Dimens.mediumPadding)) {

                OutLineEdittext(modifier = Modifier.fillMaxWidth(),hint = stringResource(id = R.string.userName), text = userName, leadingIcon = R.drawable.baseline_person_24, onTextChanged = setUserName)
                PasswordOutLineEdittext(modifier = Modifier.fillMaxWidth(),hint = stringResource(id = R.string.password), text = password, leadingIcon = R.drawable.baseline_password_24,onTextChanged = setPassword)
            }
            RoundedCornerButton(modifier = Modifier.fillMaxWidth(),text = stringResource(id = R.string.login), enabled = userName.isNotEmpty() && password.isNotEmpty(), isLoading = loading) {
                if (clientConnected){
                    viewModel.loginUser(userName, password)
                }else{
                    viewModel.connectUser(true,userName, password)
                }
                loading = true

            }
        }


    }

    override fun viewCreated() {
    }

    override fun onResume() {
        super.onResume()
        viewModel.initClientResult()
        viewModel.connectUser()
    }




}


@Composable
fun MainHeader(showLogout:Boolean = false,onLogout:() -> Unit = {}){
    Box(
        Modifier
            .fillMaxWidth()
            .padding(Dimens.largePadding)) {
        if (showLogout){
            IconButton(onClick = onLogout, modifier = Modifier.align(Alignment.CenterStart)) {
                Icon(painter = painterResource(id = R.drawable.baseline_power_settings_new_24), contentDescription = "", tint = Colors.colorError)
            }
        }

        Icon(modifier = Modifier.height(Dimens.headerIconHeight).align(Alignment.Center),painter = painterResource(id = R.drawable.ic_telnyx), contentDescription = "")
    }
}