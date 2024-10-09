package pl.pawelantonik.metronome.app

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MainApplication : Application() {

  override fun onCreate() {
    super.onCreate()

    val channel = NotificationChannel(
      "running_metronome",
      "Metronome notification",
      NotificationManager.IMPORTANCE_HIGH,
    )
    val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    notificationManager.createNotificationChannel(channel)
  }
}