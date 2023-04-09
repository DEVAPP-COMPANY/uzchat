package uz.devapp.uzchat.di

import android.content.Context
import android.util.Log
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.socket.client.IO
import io.socket.client.Socket
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import uz.devapp.uzchat.BuildConfig
import uz.devapp.uzchat.data.api.Api
import uz.devapp.uzchat.data.repository.AuthRepository
import uz.devapp.uzchat.data.repository.MainRepository
import uz.devapp.uzchat.utils.Constants
import uz.devapp.uzchat.utils.PrefUtils
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun provideAuthRepository(api: Api): AuthRepository {
        return AuthRepository(api)
    }

    @Provides
    @Singleton
    fun provideMainRepository(api: Api): MainRepository {
        return MainRepository(api)
    }

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit): Api {
        return retrofit.create(Api::class.java)
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create()
            )
            .client(okHttpClient)
            .build()
    }

    @Provides
    fun provideOkHttpClient(@ApplicationContext context: Context): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("Token", PrefUtils.getToken())
                .addHeader("Key", Constants.DEVELOPER_KEY)
                .build()
            Log.d("JW","Token: ${PrefUtils.getToken()}")
            chain.proceed(request)
        }
        if (BuildConfig.DEBUG) {
            builder.addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(ChuckerCollector(context))
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )
        }
        return builder.build()
    }

    @Provides
    @Singleton
    fun provideSocketIO(): Socket{
        return IO.socket(Constants.SOCKET_SERVER)
    }
}