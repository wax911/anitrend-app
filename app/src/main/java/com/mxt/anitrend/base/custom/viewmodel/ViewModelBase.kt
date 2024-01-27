package com.mxt.anitrend.base.custom.viewmodel

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mxt.anitrend.R
import com.mxt.anitrend.base.custom.async.RequestHandler
import com.mxt.anitrend.base.interfaces.event.ResponseCallback
import com.mxt.anitrend.base.interfaces.event.RetroCallback
import com.mxt.anitrend.util.KeyUtil
import com.mxt.anitrend.util.graphql.apiError
import retrofit2.Call
import retrofit2.Response

/**
 * Created by max on 2017/10/14.
 * View model abstraction contains the generic data model
 */

class ViewModelBase<T>: ViewModel(), RetroCallback<T> {

    val model = MutableLiveData<T?>()

    var state: ResponseCallback? = null

    private var mLoader: RequestHandler<T>? = null

    private lateinit var emptyMessage: String
    private lateinit var errorMessage: String
    private lateinit var tokenMessage: String

    val params = Bundle()

    fun setContext(context: Context?) {
        context?.apply {
            emptyMessage = getString(R.string.layout_empty_response)
            errorMessage = getString(R.string.text_error_request)
            tokenMessage = getString(R.string.text_error_auth_token)
        }
    }

    /**
     * Template to make requests for various data types from api, the
     * <br></br>
     * @param request_type the type of request to execute
     */
    fun requestData(@KeyUtil.RequestType request_type: Int, context: Context) {
        mLoader = RequestHandler(params, this, request_type)
        mLoader?.executeOnExecutor(AsyncTask.SERIAL_EXECUTOR, context)
    }

    /**
     * This method will be called when this ViewModel is no longer used and will be destroyed.
     *
     *
     * It is useful when ViewModel observes some data and you need to clear this subscription to
     * prevent a leak of this ViewModel.
     */
    override fun onCleared() {
        if (mLoader?.status != AsyncTask.Status.FINISHED)
            mLoader?.cancel(true)
        mLoader = null
        state = null
        super.onCleared()
    }

    /**
     * Invoked for a received HTTP response.
     *
     *
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call [Response.isSuccessful] to determine if the response indicates success.
     *
     * @param call     the origination requesting object
     * @param response the response from the network
     */
    override fun onResponse(call: Call<T>, response: Response<T>) {
        val container: T? = response.body()
        if (response.isSuccessful && container != null)
            model.setValue(container)
        else {
            val error = response.apiError()
            // Hacky fix that I'm ashamed of
            if (response.code() == 400 && error.contains("Invalid token"))
                state?.showError(tokenMessage)
            else if (response.code() == 401)
                state?.showError(tokenMessage)
            else
                state?.showError(error)
        }
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call      the origination requesting object
     * @param throwable contains information about the error
     */
    override fun onFailure(call: Call<T>, throwable: Throwable) {
        state?.showEmpty(throwable.message ?: errorMessage)
        throwable.printStackTrace()
    }
}
