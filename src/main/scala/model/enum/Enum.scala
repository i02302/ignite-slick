package model.`enum`

trait Enum extends Serializable with Product {

  val code: String
  val name: String

}

object Enum {

  trait EnumCompanion[E <: Enum] {

    val name: String
    val description: String
    val members: Seq[E]
    def of(code: String): Option[E] = members.find(_.code == code)

  }

}
