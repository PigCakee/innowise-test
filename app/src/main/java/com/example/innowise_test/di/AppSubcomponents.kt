package com.example.innowise_test.di

import dagger.Module

@Module(
    subcomponents = [
        ForecastComponent::class,
        TodayComponent::class,
    ]
)
class AppSubcomponents
