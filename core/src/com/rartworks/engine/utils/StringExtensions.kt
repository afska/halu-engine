package com.rartworks.engine.utils

import java.text.Normalizer

/**
 * Converts all letters to basic Latin characters and removes diacritical marks.
 * It does preserve only letters, spaces, and numbers.
 * It can trim the string to a [limit] of characters, and add "..." to the end.
 */
fun String.deburr(limit: Int? = null): String {
	val normalized = Normalizer
		.normalize(this, Normalizer.Form.NFD)
		.replace(Regex("[^A-Za-z0-9\\s]"), "")

	return(
		if (limit != null && normalized.length > limit)
			normalized.take(limit) + "..."
		else
			normalized
	)
}
