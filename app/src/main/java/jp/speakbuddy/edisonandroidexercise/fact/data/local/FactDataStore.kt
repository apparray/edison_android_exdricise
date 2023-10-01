package jp.speakbuddy.edisonandroidexercise.fact.data.local

import android.content.Context
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.google.protobuf.InvalidProtocolBufferException
import jp.speakbuddy.edisonandroidexercise.fact.domain.model.FactInfo
import kotlinx.coroutines.flow.first
import java.io.InputStream
import java.io.OutputStream

class FactDataStore(
    private val context: Context
) {
    private val Context.localFact by dataStore(
        fileName = DATA_STORE_FACT,
        serializer = FactSerializer
    )

    suspend fun getFact(): FactInfo {
        context.localFact.data.first().let {
            return FactInfo(
                fact = it.fact,
                length = it.length
            )
        }
    }

    suspend fun setFact(factInfo: FactInfo) {
        context.localFact.updateData {
            it.toBuilder()
                .setFact(factInfo.fact)
                .setLength(factInfo.length)
                .build()
        }
    }

    suspend fun clear() {
        context.localFact.updateData {
            it.toBuilder()
                .clear()
                .build()
        }
    }

    companion object {
        private const val DATA_STORE_FACT = "local_fact.pb"
    }
}

private object FactSerializer : Serializer<LocalFact> {
    override val defaultValue: LocalFact
        get() = LocalFact.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): LocalFact {
        try {
            return LocalFact.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override suspend fun writeTo(t: LocalFact, output: OutputStream) = t.writeTo(output)
}