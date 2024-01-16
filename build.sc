import mill._
import mill.scalalib._
import mill.scalalib.scalafmt._
import $ivy.`io.github.davidgregory084::mill-tpolecat::0.3.5`
import io.github.davidgregory084.TpolecatModule
import $ivy.`com.goyeau::mill-scalafix::0.3.2`
import com.goyeau.mill.scalafix.ScalafixModule
import os.RelPath

object typeclassderivation
    extends Cross[TypeclassDerivationModule]("2.13.12", "3.3.1")

trait TypeclassDerivationModule
    extends CrossScalaModule
    with ScalafmtModule
    with TpolecatModule
    with ScalafixModule
    with SemanticDbJavaModule {

  override def scalafixScalaBinaryVersion = "2.13"

  def scalafixIvyDeps = Agg(
    ivy"org.typelevel::typelevel-scalafix:0.2.0"
  )

  override def scalafixConfig = Option(
    millModuleBasePath.value / RelPath(
      "../"
    ) / s".scalafix${artifactSuffix()}.conf"
  )

  override def scalacOptions = T{
    if(artifactSuffix().startsWith("2")) super.scalacOptions() ++ Seq("-Ypatmat-exhaust-depth", "off") else super.scalacOptions()}

  object test extends ScalaTests with TestModule.Munit {
    def ivyDeps = Agg(
      ivy"org.scalameta::munit::0.7.29"
    )
  }
}
