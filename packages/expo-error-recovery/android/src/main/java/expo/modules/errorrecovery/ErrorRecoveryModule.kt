package expo.modules.errorrecovery

import android.content.Context
import android.content.SharedPreferences

import org.unimodules.core.ExportedModule
import org.unimodules.core.ModuleRegistry
import org.unimodules.core.Promise
import org.unimodules.core.interfaces.ExpoMethod

private const val RECOVERY_STORE = "expo.modules.errorrecovery.store"
private const val RECOVERY_STORE_KEY = "recoveredProps"

open class ErrorRecoveryModule(context: Context) : ExportedModule(context) {
  protected lateinit var mSharedPreferences: SharedPreferences

  override fun getName(): String = "ExpoErrorRecovery"

  override fun onCreate(moduleRegistry: ModuleRegistry) {
    mSharedPreferences = context.applicationContext.getSharedPreferences(RECOVERY_STORE, Context.MODE_PRIVATE)
  }

  @ExpoMethod
  fun saveRecoveryProps(props: String?, promise: Promise) {
    props?.let {
      setRecoveryProps(it)
    }
    promise.resolve(null)
  }

  override fun getConstants(): Map<String, Any?> {
    return mapOf("recoveredProps" to consumeRecoveryProps())
  }


  protected open fun setRecoveryProps(props: String) {
    mSharedPreferences.edit().putString(RECOVERY_STORE_KEY, props).apply()
  }

  protected open fun consumeRecoveryProps(): String? {
    return mSharedPreferences.getString(RECOVERY_STORE_KEY, null)?.let {
      mSharedPreferences.edit().remove(RECOVERY_STORE_KEY).apply()
      it
    }
  }
}
