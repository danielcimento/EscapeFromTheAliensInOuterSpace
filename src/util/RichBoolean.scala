package util

// TODO: Candidate for deletion

/**
  * Helper class used to make more convenient the pattern "if true Some else None"
  * When true, it will execute its internal operation and store the return in an
  * option, otherwise it will return None
  * @param boolean The actual boolean which is to be evaluated
  */
@Deprecated
case class RichBoolean(boolean: Boolean) {
  def whenTrue[T](operation: Unit => T): Option[T] =
    if(boolean) Some(operation()) else None
}

@Deprecated
object RichBoolean {
  implicit def boolToRichBool(boolean: Boolean): RichBoolean = RichBoolean(boolean)
}
