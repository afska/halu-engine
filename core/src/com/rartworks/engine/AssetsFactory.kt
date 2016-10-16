package com.rartworks.engine

import aurelienribon.bodyeditor.BodyEditorLoader
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver
import com.badlogic.gdx.audio.Music
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.TextureAtlas
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.utils.I18NBundle
import com.rartworks.engine.collisions.CollisionInfo
import com.rartworks.engine.drawables.MovieClipInfo
import com.badlogic.gdx.physics.box2d.World as Box2dWorld

/**
 * The manager of the principal game engine assets.
 */
object AssetsFactory {
	val sprites: TextureAtlas get() =
		this.manager.get(this.spritesPath, TextureAtlas::class.java)

	lateinit var manager: AssetManager
	lateinit var box2dWorld: Box2dWorld

	lateinit var polygons: BodyEditorLoader
	private lateinit var spritesPath: String

	/**
	 * Creates the AssetManager.
	 */
	fun init() {
		this.manager = AssetManager()
	}

	/**
	 * Loads with *LibGDX*'s [AssetManager]:
	 *   - A .atlas with the sprites at [spritesPath]
	 *   - A .json with the polygons at [polygonsPath]
	 */
	fun load(spritesPath: String, polygonsPath: String): AssetsFactory {
		val fileResolver = InternalFileHandleResolver()
		this.manager.setLoader(FreeTypeFontGenerator::class.java, FreeTypeFontGeneratorLoader(fileResolver))
		this.manager.setLoader(BitmapFont::class.java, ".ttf", FreetypeFontLoader(fileResolver))

		this.manager.load(spritesPath, TextureAtlas::class.java)
		this.spritesPath = spritesPath

		// Box2D stuff
		this.box2dWorld = Box2dWorld(Vector2(), true)
		this.polygons = BodyEditorLoader(Gdx.files.internal(polygonsPath))

		return this
	}

	/**
	 * Creates the [MovieClipInfo] of a [regionName] based on the loaded sprites.
	 */
	fun createMovieClipInfo(regionName: String, collisionInfo: CollisionInfo? = null): MovieClipInfo {
		return MovieClipInfo(regionName, this.sprites.createSprites(regionName), collisionInfo)
	}

	/**
	 * Loads a texture into the [AssetsFactory].
	 */
	fun loadTexture(name: String): AssetsFactory {
		this.manager.load(name, Texture::class.java)
		return this
	}

	/**
	 * Loads a font into the [AssetsFactory].
	 */
	fun loadFont(name: String, size: Int, fontPath: String): AssetsFactory {
		this.manager.load("$name.ttf", BitmapFont::class.java, this.createFontLoader(fontPath, size, Color.WHITE))
		return this
	}

	/**
	 * Loads a music into the [AssetsFactory].
	 */
	fun loadMusic(name: String): AssetsFactory {
		this.manager.load(name, Music::class.java)
		return this
	}

	/**
	 * Loads an i18n bundle into the [AssetsFactory].
	 */
	fun loadI18nBundle(name: String): AssetsFactory {
		this.manager.load(name, I18NBundle::class.java)
		return this
	}

	fun getTexture(name: String) = this.manager.get(name, Texture::class.java)
	fun getI18n(name: String) = this.manager.get(name, I18NBundle::class.java)
	fun getMusic(name: String) = this.manager.get(name, Music::class.java)
	fun getFont(name: String) = this.manager.get("${name}.ttf", BitmapFont::class.java)

	/**
	 * Disposes all the assets.
	 */
	fun dispose() {
		this.manager.dispose()
		this.box2dWorld.dispose()
	}

	/**
	 * Creates a [FreetypeFontLoader.FreeTypeFontLoaderParameter] using the [fileName], with a [size] and a [color].
	 */
	private fun createFontLoader(fileName: String, size: Int, color: Color): FreetypeFontLoader.FreeTypeFontLoaderParameter {
		val parameter = FreetypeFontLoader.FreeTypeFontLoaderParameter()
		parameter.fontFileName = fileName
		parameter.fontParameters = FreeTypeFontGenerator.FreeTypeFontParameter()
		parameter.fontParameters.size = size
		parameter.fontParameters.color = color

		return parameter
	}
}
