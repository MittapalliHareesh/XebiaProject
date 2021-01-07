package com.xebia.myapplication.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xebia.myapplication.model.APIResult
import com.xebia.myapplication.model.Entry
import com.xebia.myapplication.model.ProgressBarStatus
import com.xebia.myapplication.repository.FeedDataRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel : ViewModel() {

    private var repository = FeedDataRepository()

    var entryList = MutableLiveData<List<Entry>>()

    var progressBarStatus = MutableLiveData<ProgressBarStatus>()

    fun getImagesList() {
        progressBarStatus.value = ProgressBarStatus(true,"")
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getImagesList()
            withContext(Dispatchers.Main) {
                if (response.status == APIResult.Status.SUCCESS && response.data != null) {
                    entryList.value = response.data.entryList
                    progressBarStatus.value = ProgressBarStatus(false, "")
                } else {
                    progressBarStatus.value = ProgressBarStatus(false, response.message.toString())
                }
            }
        }
    }
}