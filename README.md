# typeclass-derivation

This repository is an exercise for an interview.

The exercise is first to implement show for a Tree in an unfamiliar
codebase, then to implement show with typeclass derivation for both
scala 2.13 and scala 3.3.1, using magnolia or shapely, your
choice. You may ask the interviewer(s) any questions you want to ask,
even if you do not know how to accomplish the task, as if you were
pair programming with the interviewer(s). Please talk through your
process of implementation as you go.

You can use Google to look anything up you need to accomplish the task.

For all possible implementations, the program should output:

```
Branch(Branch(Branch(Branch(Branch(Branch(Leaf(1), Leaf(2)), Leaf(3)), Leaf(4)), Leaf(5)), Leaf(6)), Leaf(7))
Leaf(1), Leaf(2), Leaf(3), Leaf(4), Leaf(5), Leaf(6), Leaf(7),
```

when run with the appropriate run instructions:

1. `./mill typeclassderivation[3.3.1].run` for scala 3.
1. `./mill typeclassderivation[2.13.12].run` for scala 2

## Build Tool

This uses the [mill](https://mill-build.com/mill/Intro_to_Mill.html) scala build tool.

You may in some cases need to create additional source folders for the
specialized code needed for Scala 2 and Scala 3. The build is already
setup to handle these, all you must do is add the
`typeclassderivation/src-2/` and `typeclassderivation/src-3/`
directories, for scala 2 and scala 3 version-specific sources,
respectively.

## Shapely

Shapely is a typeclass derivation library for Scala 2 and 3 by Sam
Halliday. It provides the means to generate typeclass instances for
coproducts (sealed traits) and products (case classes) generically
with only the smallest amount of macros possible. You may add the
following to the build by adding the appropriate ivy dependencies to
`build.sc`:

```scala
  override def ivyDeps = Agg(
    ivy"com.fommil::shapely:1.0.0"
```

## Magnolia

Magnolia is a typeclass derivation library for Scala 2 and 3 by
SoftwareMill. It provides the means to generate typeclass instances
for coproducts (sealed traits) and products (case classes)
generically. It is macro-based, and as such uses two different
artifacts for 2 and 3. You may add the following to the build by
adding the appropriate ivy dependencies to `build.sc`:

```scala
  def ivyDeps = T{
    if (scalaVersion().startsWith("2")){
      Agg(ivy"com.softwaremill.magnolia1_2::magnolia:1.1.8", ivy"org.scala-lang:scala-reflect:scalaVersion()")
    } else Agg(ivy"com.softwaremill.magnolia1_3::magnolia:1.3.4")
  }
```

and by adding the following to `build.sc`:

```scala
  override def scalacOptions = T {
    if (artifactSuffix().startsWith("2"))
      super.scalacOptions() ++ Seq("-Ypatmat-exhaust-depth", "off")
    else super.scalacOptions()
  }
```



