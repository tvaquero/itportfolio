

(define (problem BW-rand-10)
(:domain blocksworld)
(:objects b1 b2 b3 b4 b5 b6 b7 b8 b9 b10  - block)
(:init
(handempty)
(on b1 b3)
(ontable b2)
(on b3 b2)
(on b4 b10)
(ontable b5)
(on b6 b9)
(on b7 b8)
(on b8 b4)
(on b9 b5)
(ontable b10)
(clear b1)
(clear b6)
(clear b7)
)
(:goal
(and
(on b1 b3)
(on b2 b1)
(on b4 b9)
(on b6 b8)
(on b7 b10)
(on b8 b7)
(on b10 b2))
)
)


