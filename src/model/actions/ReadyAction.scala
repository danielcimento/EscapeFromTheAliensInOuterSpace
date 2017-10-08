package model.actions
import java.util.UUID

case class ReadyAction(ready: Boolean) extends Action {
  override val uuid: UUID = UUID.randomUUID()
}