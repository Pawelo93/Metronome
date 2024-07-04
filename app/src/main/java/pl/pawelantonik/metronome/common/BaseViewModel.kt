package pl.pawelantonik.metronome.common

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel : ViewModel(), ErrorEmitter {
  private val _error = MutableStateFlow<Throwable?>(null)
  override val error: StateFlow<Throwable?> = _error

  private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
    processError(throwable)
  }

  fun processError(throwable: Throwable) {
    Log.e("BaseViewModel", throwable.toString())
    viewModelScope.launch { _error.emit(throwable) }
  }

  protected fun launchWithDefaultErrorHandler(block: suspend () -> Unit) {
    viewModelScope.launch(exceptionHandler) {
      block()
    }
  }
}

interface ErrorEmitter {
  val error: StateFlow<Throwable?>
}