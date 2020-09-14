package dev.hello.viper

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ccpp.pgw.sdk.android.builder.CreditCardPaymentBuilder
import com.ccpp.pgw.sdk.android.builder.TransactionRequestBuilder
import com.ccpp.pgw.sdk.android.callback.TransactionResultCallback
import com.ccpp.pgw.sdk.android.core.PGWSDK
import com.ccpp.pgw.sdk.android.enums.APIResponseCode
import com.ccpp.pgw.sdk.android.model.api.response.TransactionResultResponse
import com.ccpp.pgw.sdk.android.model.payment.CreditCardPayment
import dev.hello.viper.utils.AppConstant
import dev.hello.viper.utils.CommonUtils
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        tv_add.text = hello(4, 5)
        hello(4, 8)

    }

    fun hello(string1: Int, string2: Int): String {
        Log.e("add--", (string1 + string2).toString())
        return (string1 + string2).toString()
    }


    override fun onResume() {
        super.onResume()
        setUpPayment()
    }


    private fun setUpPayment() {
        val creditCardPayment: CreditCardPayment =
            CreditCardPaymentBuilder("4111111111111111")
                .setExpiryMonth(12)
                .setExpiryYear(2020)
                .setSecurityCode("123").build()


        val transactionRequest =
            TransactionRequestBuilder(AppConstant.paymentToken)
                .withCreditCardPayment(creditCardPayment)
                .build()


        PGWSDK.getInstance()
            .proceedTransaction(transactionRequest, object : TransactionResultCallback {
                override fun onResponse(response: TransactionResultResponse?) {
                    if (response!!.responseCode.equals(APIResponseCode.TRANSACTION_COMPLETED)) {
                        val transactionId = response.transactionID
                        CommonUtils.log("2c2pPayment", transactionId)
                    } else {
                        CommonUtils.log(
                            "2c2pResponse",
                            response.responseCode + "/" + response.responseDescription
                        )
                    }
                }

                override fun onFailure(e: Throwable?) {
                    e!!.printStackTrace()
                }
            })
    }
}
