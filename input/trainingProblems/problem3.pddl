

(define (problem BW-rand-20)
(:domain blocksworld)
(:objects b1 b2 b3 b4 b5 b6 b7 b8 b9 b10 b11 b12 b13 b14 b15 b16 b17 b18 b19 b20  - block)
(:init
(handempty)
(on b1 b19)
(on b2 b3)
(on b3 b4)
(on b4 b14)
(on b5 b18)
(on b6 b8)
(on b7 b9)
(on b8 b20)
(on b9 b11)
(on b10 b12)
(on b11 b10)
(ontable b12)
(ontable b13)
(ontable b14)
(on b15 b17)
(on b16 b15)
(on b17 b5)
(on b18 b6)
(on b19 b13)
(on b20 b2)
(clear b1)
(clear b7)
(clear b16)
)
(:goal
(and
(on b1 b17)
(on b2 b8)
(on b3 b16)
(on b4 b20)
(on b6 b13)
(on b7 b15)
(on b8 b7)
(on b9 b19)
(on b10 b1)
(on b11 b2)
(on b12 b5)
(on b13 b18)
(on b14 b10)
(on b15 b14)
(on b18 b4)
(on b19 b11)
(on b20 b9))
)
)

