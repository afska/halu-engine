package com.rlabs.ChangeMe.screens.input

import com.rlabs.ChangeMe.screens.GameScreen
import com.rlabs.engine.events.InputHandler

/**
 * The manager of the input of the game. It reacts to certain events.
 */
class GameController(private val screen: GameScreen) : InputHandler(screen.dimensions) {
	/**
	 * Registers the handlers of the touch down/up events.
	 */
	override fun register(): InputHandler {
		return super.register()
			.onTouchDown { x, y, pointer ->
				this.screen.hello.position.y -= 10f
				true
			}.onTouchUp { x, y, pointer ->
				this.screen.hello.position.y += 10f
				true
			}
	}
}
