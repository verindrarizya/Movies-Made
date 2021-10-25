package com.verindrarizya.movies.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.verindrarizya.movies.R
import com.verindrarizya.movies.work.NotificationWorker
import java.util.concurrent.TimeUnit

class SettingFragment: PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_main, rootKey)

        val notificationWorkRequest = PeriodicWorkRequestBuilder<NotificationWorker>(2, TimeUnit.DAYS)
            .setInitialDelay(2, TimeUnit.DAYS)
            .build()
        val workManager = WorkManager.getInstance(requireContext())

        val notificationPreference = findPreference<SwitchPreferenceCompat>(getString(R.string.key_notification_preference))
        notificationPreference?.setOnPreferenceChangeListener { _, newValue ->
            if (newValue == true) {
                workManager.enqueueUniquePeriodicWork(
                    notificationWorkName,
                    ExistingPeriodicWorkPolicy.REPLACE,
                    notificationWorkRequest
                )
            } else {
                workManager.cancelUniqueWork(notificationWorkName)
            }
            true
        }


    }

    companion object {
        private const val notificationWorkName = "reminderWork"
    }
}