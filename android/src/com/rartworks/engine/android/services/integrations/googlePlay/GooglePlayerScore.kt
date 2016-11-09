package com.rartworks.engine.android.services.integrations.googlePlay

import com.rartworks.engine.apis.PlayerScore

data class GooglePlayerScore(override val name: String, override val score: Int) : PlayerScore { }
