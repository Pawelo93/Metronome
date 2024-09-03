package pl.pawelantonik.metronome.feature.service

import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat
import dagger.hilt.android.AndroidEntryPoint
import pl.pawelantonik.metronome.MainActivity
import pl.pawelantonik.metronome.R
import pl.pawelantonik.metronome.feature.service.domain.IsMetronomeRunningRepository
import javax.inject.Inject

@AndroidEntryPoint
class MetronomeService : Service() {

  companion object {
    fun getStartIntent(context: Context) = Intent(context, MetronomeService::class.java).also {
      it.action = ServiceActions.START.toString()
    }

    fun getStopIntent(context: Context) = Intent(context, MetronomeService::class.java).also {
      it.action = ServiceActions.STOP.toString()
    }
  }

  @Inject
  lateinit var isMetronomeRunningRepository: IsMetronomeRunningRepository

  @Inject
  lateinit var ticker: Ticker

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    when (intent?.action) {
      ServiceActions.START.toString() -> {
        ticker.start()
        start()
        return START_STICKY
      }

      ServiceActions.STOP.toString() -> {
        isMetronomeRunningRepository.set(false)
        ticker.stop()
        stopSelf()
      }
    }
    return super.onStartCommand(intent, flags, startId)
  }

  override fun onDestroy() {
    super.onDestroy()
    ticker.stop()
  }

  private fun start() {
    val stopActionPendingIntent = PendingIntent.getForegroundService(
      this,
      0,
      getStopIntent(this),
      PendingIntent.FLAG_IMMUTABLE,
    )

    val startAppIntent = Intent(this, MainActivity::class.java).apply {
      flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
    }

    val startAppPendingIntent: PendingIntent = PendingIntent.getActivity(
      this,
      0,
      startAppIntent,
      PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
    )

    val notification = NotificationCompat.Builder(this, "running_metronome")
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentTitle("Metronome is running")
      .setOngoing(true)
      .setSilent(true)
      .setContentIntent(startAppPendingIntent)
      .addAction(R.drawable.music_logo, "Stop", stopActionPendingIntent)
      .build()

    startForeground(1, notification)

    isMetronomeRunningRepository.set(true)
  }

  enum class ServiceActions {
    START,
    STOP
  }
}
