package com.rartworks.engine.rendering

//import com.badlogic.gdx.graphics.g2d.BitmapFont // DEBUG

//import com.badlogic.gdx.graphics.glutils.ShapeRenderer // PERFORMANCE
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer
import com.badlogic.gdx.utils.Disposable
//import com.rartworks.engine.AssetsFactory

class Renderer(private val dimensions: Dimensions) : Disposable {
	val camera: OrthographicCamera = OrthographicCamera()
	private val spriteBatch = SpriteBatch()
	//private val shapeRenderer = ShapeRenderer() // PERFORMANCE
	private val box2dDebugRenderer = Box2DDebugRenderer() // DEBUG
	//private val fpsFont = BitmapFont() // DEBUG

	init {
		this.camera.setToOrtho(false, this.dimensions.width, this.dimensions.height)
		this.spriteBatch.projectionMatrix = this.camera.combined
	}

	fun render(draw: (SpriteBatch) -> (Unit)/*, drawShapes: (ShapeRenderer) -> (Unit) = {} // PERFORMANCE */) {
		this.cleanScreen()

		this.spriteBatch.begin()
		this.spriteBatch.transformMatrix.setToTranslation(0f, 0f, 0f)
		draw(this.spriteBatch)
		//this.fpsFont.draw(this.spriteBatch, Gdx.graphics.framesPerSecond.toString(), 10f, 20f) // DEBUG
		this.spriteBatch.end()

		/* // PERFORMANCE
		this.shapeRenderer.begin(ShapeRenderer.ShapeType.Line)
		drawShapes(this.shapeRenderer)
		this.shapeRenderer.end()
		*/

		//this.box2dDebugRenderer.render(AssetsFactory.box2dWorld, this.camera.combined) // DEBUG
	}

	override fun dispose() {
		this.spriteBatch.dispose()
	}

	private fun cleanScreen() {
		Gdx.gl.glClearColor(0f, 0f, 0f, 1f)
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
	}
}
