package com.app.cryptotracker.di

import com.app.cryptotracker.core.data.networking.HttpClientFactory
import com.app.cryptotracker.crypto.data.networking.RemoteCoinDataSource
import com.app.cryptotracker.crypto.domain.CoinDataSource
import com.app.cryptotracker.crypto.presentation.coin_list.CoinListViewModel
import io.ktor.client.engine.cio.CIO
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

/**
 * appModule is the Koin module for the application.
 *
 * This module defines the dependencies for the application, including:
 * - HttpClient: Configured with the CIO engine.
 * - CoinDataSource: Bound to RemoteCoinDataSource.
 * - CoinListViewModel: The ViewModel for the coin list screen.
 */
val appModule = module {
    /**
     * Provides a singleton instance of HttpClient.
     *
     * This instance is created using the HttpClientFactory and the CIO engine.
     */
    single { HttpClientFactory.create(CIO.create()) }

    /**
     * Provides a singleton instance of RemoteCoinDataSource and binds it to the CoinDataSource interface.
     *
     * This allows for dependency injection of CoinDataSource, while using RemoteCoinDataSource as the concrete implementation.
     */
    singleOf(::RemoteCoinDataSource).bind<CoinDataSource>()

    /**
     * Provides a ViewModel instance of CoinListViewModel.
     *
     * This allows for dependency injection of CoinListViewModel into Activities or Fragments.
     */
    viewModelOf(::CoinListViewModel)
}