package model

import java.time.{LocalDate, ZonedDateTime}
import java.util.UUID

import model.`enum`.Sex

final case class Person(
                         id: Long,
                         name: String,
                         sex: Option[Sex],
                         birthday: LocalDate,
                         father: Option[Long],
                         mother: Option[Long]
                       )
