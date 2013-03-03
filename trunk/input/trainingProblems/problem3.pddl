

(define (problem BW-rand-10)
(:domain blocksworld)
(:objects b1 b2 b3 b4 b5 b6 b7 b8 b9 b10  - block)
(:init
(handempty)
(on b1 b10)
(on b2 b7)
(on b3 b1)
(on b4 b9)
(ontable b5)
(on b6 b4)
(on b7 b8)
(on b8 b5)
(on b9 b2)
(on b10 b6)
(clear b3)
)
(:goal
(and
(on b1 b9)
(on b2 b7)
(on b4 b5)
(on b6 b3)
(on b7 b8)
(on b8 b10)
(on b9 b4)
(on b10 b1))
)
)


