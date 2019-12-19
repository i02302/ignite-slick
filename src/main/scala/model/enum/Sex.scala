package model.`enum`

import model.`enum`.Enum.EnumCompanion

sealed abstract class Sex(override val code: String, override val name: String) extends Enum

object Sex extends EnumCompanion[Sex] {

  val name        = "Sex"
  val description = "性別"

  case object Male   extends Sex(code = "M", name = "男性")
  case object Female extends Sex(code = "F", name = "女性")

  override val members: Seq[Sex] = Seq(
    Male,
    Female
  )

}
