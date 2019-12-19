name := "ignite-slick"

version := "0.1"

scalaVersion := "2.13.1"

val igniteVersion = "2.7.6"
val slickVersion  = "3.3.2"
libraryDependencies ++= Seq(
  "org.apache.ignite"  % "ignite-core"     % igniteVersion,
  "org.apache.ignite"  % "ignite-clients"  % igniteVersion,
  "com.typesafe.slick" %% "slick"          % slickVersion,
  "com.typesafe.slick" %% "slick-hikaricp" % slickVersion,
  "org.scalatest"      %% "scalatest"      % "3.2.0-M1" % Test
)
