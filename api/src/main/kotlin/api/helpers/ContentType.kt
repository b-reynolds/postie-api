package api.helpers

/**
 * MIME Content Types.
 */
object ContentType {
    /**
     * Application content type.
     */
    object Application {
        private const val PREFIX = "application/"

        /**
         * JSON subtype.
         */
        const val JSON = "$PREFIX/json"
    }
}
