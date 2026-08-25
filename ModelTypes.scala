//> using jvm "temurin:25"
//> using scala "3.9.0-RC6"
//> using dep "io.bullet::borer-core:1.17.0"
//> using dep "io.bullet::borer-derivation:1.17.0"

package net.gamatron.engine

import io.bullet.borer.Codec
import io.bullet.borer.derivation.MapBasedCodecs.*

object ModelTypesOpaqueMath {
  opaque type Vector3f = String
  object Vector3f {
    object V3 {
      def encode(value: Vector3f): V3 = V3(value)
    }
    case class V3(value: String) derives Codec {
      def get: Vector3f = value
    }
  }

  opaque type Quaternion = String
  object Quaternion {
    object Q {
      def encode(value: Quaternion): Q = Q(value)
    }
    case class Q(value: String) derives Codec {
      def get: Quaternion = value
    }
  }
}

trait ModelTypes {
  export ModelTypesOpaqueMath.{Vector3f, Quaternion}

  object PositionPickle {
    def encode(position: Position) = new PositionPickle(Vector3f.V3.encode(position.pos), Quaternion.Q.encode(position.orientation))
  }
  case class PositionPickle(pos: Vector3f.V3, orientation: Quaternion.Q) derives Codec {
    def get = Position(pos.get, orientation.get)
  }

  case class Position(pos: Vector3f, orientation: Quaternion)
}
