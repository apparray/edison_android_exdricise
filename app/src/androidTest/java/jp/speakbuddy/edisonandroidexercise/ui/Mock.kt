package jp.speakbuddy.edisonandroidexercise.ui

import io.mockk.spyk
import jp.speakbuddy.edisonandroidexercise.Constants
import jp.speakbuddy.edisonandroidexercise.data.local.`interface`.Storage
import jp.speakbuddy.edisonandroidexercise.data.network.view_data.Fact
import jp.speakbuddy.edisonandroidexercise.ui.Mock.catsFactMock
import jp.speakbuddy.edisonandroidexercise.ui.Mock.longLength
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf


object Mock {
    const val catsFactMock =
        "Lorem ipsum dolor sit cats, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat."
    const val factMock =
        "Lorem ipsum dolor sit, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua."
    const val longLength = 150
    const val shortLength = 96
}

val factObjectMock = Fact(catsFactMock, longLength)

val storage: Storage<Fact> = spyk(object : Storage<Fact> {
    override fun insert(data: Fact): Flow<Int> {
        return flowOf(Constants.DataStore.OPERATION_SUCCESS)
    }

    override fun insert(data: List<Fact>): Flow<Int> {
        return flowOf(Constants.DataStore.OPERATION_SUCCESS)
    }

    override fun get(where: (Fact) -> Boolean): Flow<Fact> {
        return flowOf(factObjectMock)
    }

    override fun getAll(): Flow<List<Fact>> {
        return flowOf(listOf(factObjectMock))
    }

    override fun clearAll(): Flow<Int> {
        return flowOf(Constants.DataStore.OPERATION_SUCCESS)
    }
})

