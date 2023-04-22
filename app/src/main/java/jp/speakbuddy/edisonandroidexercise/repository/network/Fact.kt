package jp.speakbuddy.edisonandroidexercise.repository.network

import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import java.io.InputStream
import java.io.OutputStream

@Serializable
data class Fact(
    val fact: String,
    val length: Int
)

fun FactResponse.toFact(): Fact = Fact(
    fact = fact,
    length = length
)

@Serializable
data class Facts(
    val data: List<Fact> = mutableListOf()
)

object FactsSerializer : Serializer<Facts> {

    override val defaultValue = Facts(mutableListOf())

    override suspend fun readFrom(input: InputStream): Facts {
        try {
            return Json.decodeFromString(
                Facts.serializer(), input.readBytes().decodeToString()
            )
        } catch (serialization: SerializationException) {
            throw CorruptionException("Unable to read UserPrefs", serialization)
        }
    }

    override suspend fun writeTo(t: Facts, output: OutputStream) {
        output.write(
            Json.encodeToString(Facts.serializer(), t)
                .encodeToByteArray()
        )
    }
}

