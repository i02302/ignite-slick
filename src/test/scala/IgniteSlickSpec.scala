import java.time.LocalDate

import model.Tables.profile.api._
import model.`enum`.Sex
import model.{Person, Tables}
import org.scalatest._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AsyncWordSpec
import slick.jdbc.PostgresProfile.backend.Database

import scala.concurrent.Await
import scala.concurrent.duration.Duration

class IgniteSlickSpec extends AsyncWordSpec with BeforeAndAfterAll with Matchers {

  lazy val db = Database.forConfig("ignite.db")

  val namihei = Person(id = 1L, name = "磯野波平", sex = Some(Sex.Male), father = None, mother = None, birthday = LocalDate.of(1895, 9, 14))
  val fune = Person(id = 2L, name = "磯野フネ", sex = Some(Sex.Female), father = None, mother = None, birthday = LocalDate.of(1901, 1, 11))
  val sazae = Person(id = 3L, name = "フグ田サザエ", sex = Some(Sex.Female), father = Some(1L), mother = Some(2L), birthday = LocalDate.of(1922, 11, 22))
  val katsuo = Person(id = 4L, name = "磯野カツオ", sex = Some(Sex.Male), father = Some(1L), mother = Some(2L), birthday = LocalDate.of(1938, 3, 11))
  val wakame = Person(id = 5L, name = "磯野ワカメ", sex = Some(Sex.Female), father = Some(1L), mother = Some(2L), birthday = LocalDate.of(1942, 6, 15))
  val masuo = Person(id = 6L, name = "フグ田マスオ", sex = Some(Sex.Male), father = None, mother = None, birthday = LocalDate.of(1917, 4, 3))
  val tara = Person(id = 7L, name = "フグ田タラオ", sex = Some(Sex.Male), father = Some(6L), mother = Some(3L), birthday = LocalDate.of(1948, 3, 18))
  val isonoFamily = Seq(namihei, fune, sazae, katsuo, wakame, masuo, tara)

  override def beforeAll(): Unit = {
    // init ignite
    Await.result(db.run(Tables.schema.createIfNotExists), Duration.Inf)
    Await.result(db.run(DBIO.sequence(isonoFamily.map(Tables.Persons.insertOrUpdate))), Duration.Inf)
  }

  "磯野家" should {

    "サザエさんの兄弟はカツオとワカメ" in {
      val query = Tables.Persons.filter(x => x.mother === sazae.mother || x.father === sazae.father)
        .filterNot(_.id === sazae.id)
        .sortBy(_.id)
        .map(x => (x.id, x.name)).result
      db.run(query).map(x => assert(x.toSeq === Seq(katsuo, wakame).map(x => (x.id, x.name))))
    }

    "サザエさんの夫はマスオさん" in {
      val action = for {
        children <-  Tables.Persons.filter(_.mother === sazae.id)
        husband <- Tables.Persons.filter(_.id === children.father)
      } yield (husband.id, husband.name)
      val query = action.result
      db.run(query).map(x => assert(x.toSeq === Seq(masuo).map(x => (x.id, x.name))))
    }

  }


}
