import javafx.beans.value.{ChangeListener, ObservableValue}
import javafx.scene.control.TextField

package object view {

  // TODO: Add a handler to prevent users from inputting ports higher than the allowed range
  def numericRegexListener(textField: TextField): ChangeListener[String] = {
    new ChangeListener[String] {
      override def changed(observable: ObservableValue[_ <: String], oldValue: String, newValue: String) = {
        if (!newValue.matches("\\d*"))
          textField.setText(newValue.replaceAll("[^\\d]", ""))
      }
    }
  }

  def parsePortNumber(portNumberText: String): Int = {
    try {
      portNumberText.toInt
    } catch {
      case exc: NumberFormatException =>
        exc.printStackTrace()
        6789
    }
  }
}
