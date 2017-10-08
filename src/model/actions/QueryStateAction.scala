package model.actions
import java.util.UUID

case class QueryStateAction() extends Action {
  override val uuid: UUID = UUID.randomUUID()
}
