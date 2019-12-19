package model

import java.time.{LocalDate, ZonedDateTime}
import java.util.Date

import model.`enum`.Sex

// AUTO-GENERATED Slick data model
/** Stand-alone Slick data model for immediate use */
object Tables extends {
  val profile = slick.jdbc.PostgresProfile
} with Tables

/** Slick data model trait for extension, choice of backend or usage in the cake pattern. (Make sure to initialize this late.) */
trait Tables {
  val profile: slick.jdbc.JdbcProfile

  import profile.api._

  /** DDL for all tables. Call .create to execute. */
  lazy val schema: profile.SchemaDescription =
    Array(Persons.schema).reduceLeft(_ ++ _)
  @deprecated("Use .schema instead of .ddl", "3.0")
  def ddl = schema

  final class PersonRow(tag: Tag) extends Table[Person](tag, "Campaigns") {

    def id = column[Long]("id", O.PrimaryKey)

    def name = column[String]("name")

    def sex = column[Option[String]]("sex")

    def birthday = column[LocalDate]("birthday")

    def father = column[Option[Long]]("father")

    def mother = column[Option[Long]]("mother")

    def * =
      (
        id,
        name,
        sex,
        birthday,
        father,
        mother
      ).shaped <> ({
        case (id, name, sex, birthday, father, mother) =>
          Person(
            id, name, sex.flatMap(Sex.of), birthday, father, mother
          )
      }, { person: Person =>
        Some(
          person.id, person.name, person.sex.map(_.code), person.birthday, person.father, person.mother
        )
      })
  }

  lazy val Persons = new TableQuery(tag => new PersonRow(tag))

}
