package com.rlabs.engine.android.services.integrations.googlePlay

import com.rlabs.engine.apis.PlayerScore

data class GooglePlayerScore(override val name: String, override val score: Int) : PlayerScore { }
