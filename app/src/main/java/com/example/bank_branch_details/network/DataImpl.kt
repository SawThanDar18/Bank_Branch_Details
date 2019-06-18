package com.example.bank_branch_details.network

import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.bank_branch_details.event.RestApiEvents
import com.example.bank_branch_details.network.api.Data
import com.example.bank_branch_details.network.api.RequestAuthApi
import com.example.bank_branch_details.network.api.RequestBranchDetailApi
import com.example.bank_branch_details.network.model.Access_BranchCode
import com.example.bank_branch_details.network.model.Access_BranchInfo
import com.example.bank_branch_details.network.model.Access_Token
import com.example.bank_branch_details.network.response.BranchCodeResponse
import com.example.bank_branch_details.utils.Constant
import com.google.gson.Gson
import okhttp3.OkHttpClient
import org.greenrobot.eventbus.EventBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

open class DataImpl private constructor() : Data{

    private  var requestAuthApi : RequestAuthApi
    private  var requestTokenApi: RequestBranchDetailApi
    private var context : Context? = null
    private var token : String? = null

    companion object{
        private var INSTANCE : DataImpl? = null
        fun getInstance() : DataImpl{
            if(INSTANCE == null){
                INSTANCE = DataImpl()
            }
            val instance = INSTANCE
            return instance!!
        }
    }

    init {

        fun getOkHttpClient(): OkHttpClient {
            try {
                val sslContext = SSLContext.getInstance("SSL")

                // Create a trust manager that does not validate certificate chains
                val trustAllCerts = arrayOf<TrustManager>(object : X509TrustManager {
                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }

                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(chain: Array<java.security.cert.X509Certificate>, authType: String) {
                    }
                })

                sslContext.init(null, trustAllCerts, java.security.SecureRandom())

                // Create an ssl socket factory with our all-trusting manager
                val sslSocketFactory = sslContext.socketFactory

                val builder = OkHttpClient.Builder()
                builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
                builder.hostnameVerifier { hostname, session -> true }
                builder.connectTimeout(1, TimeUnit.MINUTES)
                builder.readTimeout(1, TimeUnit.MINUTES)
                builder.writeTimeout(1, TimeUnit.MINUTES)

                return builder.build()

            } catch (e: Exception) {
                throw RuntimeException(e)
            }
        }

        val authClient = getOkHttpClient()
        val authRetrofit = Retrofit.Builder()
            .baseUrl(Constant.RequstToken_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(authClient)
            .build()
        requestAuthApi = authRetrofit.create(RequestAuthApi::class.java)

        val detailClient = getOkHttpClient()
        val detailRetrofit = Retrofit.Builder()
            .baseUrl(Constant.BranchDetail_URL)
            .addConverterFactory(GsonConverterFactory.create(Gson()))
            .client(detailClient)
            .build()
        requestTokenApi = detailRetrofit.create(RequestBranchDetailApi::class.java)
    }

    override fun getRequestAuth() {
      requestAuthApi.getRequestAuth("KCA","KCA","password").enqueue(object : Callback<Access_Token>{
          override fun onFailure(call: Call<Access_Token>, t: Throwable) {
              EventBus.getDefault()
                  .post(
                      RestApiEvents.ErrorInvokingAPIEvent(
                      t.localizedMessage
                  ))
          }

          override fun onResponse(call: Call<Access_Token>, response: Response<Access_Token>) {
             if(response.isSuccessful){
                 Log.i("login","if")
                 token = response.body()!!.access_token
                 getBranchDetail()
                 getCurrentPosition()
             } else {
                 Log.i("login","else")
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(
                        "No data found"
                    ))
             }
          }
      })
    }

    override fun getBranchDetail(){
        val branch = Access_BranchCode("101","5.01")
        requestTokenApi.getBranchDetail("Bearer ${token}", branch).enqueue(object : Callback<BranchCodeResponse>{
            override fun onFailure(call: Call<BranchCodeResponse>, t: Throwable) {
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(
                        t.localizedMessage
                    ))
            }

            override fun onResponse(call: Call<BranchCodeResponse>, response: Response<BranchCodeResponse>) {
                if(response.isSuccessful){
                    Log.i("msg","if")
                    EventBus.getDefault()
                        .post(RestApiEvents.ShowBranchDetails(response.body()!!))
                }
                else{
                    Log.i("msg","else")
                    Toast.makeText(context, "err", Toast.LENGTH_LONG).show()
                }
            }
        })
    }

    override fun getCurrentPosition() {
        val branch = Access_BranchCode("101","5.01")
        requestTokenApi.getCurrentPosition("Bearer ${token}", branch).enqueue(object: Callback<BranchCodeResponse>{
            override fun onFailure(call: Call<BranchCodeResponse>, t: Throwable) {
                EventBus.getDefault()
                    .post(RestApiEvents.ErrorInvokingAPIEvent(t.localizedMessage))
            }

            override fun onResponse(call: Call<BranchCodeResponse>, response: Response<BranchCodeResponse>) {
                if(response.isSuccessful){
                    EventBus.getDefault()
                        .post(RestApiEvents.ShowCurrentPosition(response.body()!!))
                }
                else{
                    Toast.makeText(context, "err", Toast.LENGTH_LONG).show()
                }
            }
        })
    }
}

