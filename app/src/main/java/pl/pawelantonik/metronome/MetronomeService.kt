package pl.pawelantonik.metronome

import android.app.Service
import android.content.Intent
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MetronomeService : Service() {

  override fun onBind(intent: Intent?): IBinder? {
    return null
  }

  override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
    when (intent?.action) {
      ServiceActions.START.toString() -> start()
      ServiceActions.STOP.toString() -> stopSelf()
    }
    return super.onStartCommand(intent, flags, startId)
  }

  private fun start() {
    val notification = NotificationCompat.Builder(this, "running_metronome")
      .setSmallIcon(R.drawable.ic_launcher_foreground)
      .setContentTitle("Metronome is running")
      .setContentText("Elapsed time: 00:50")
      .build()

    startForeground(1, notification)
  }

  enum class ServiceActions {
    START,
    STOP
  }
}