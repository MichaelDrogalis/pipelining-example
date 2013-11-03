# pipelining-example

An example of pipelining in Clojure using core.async.

## Usage

```clojure
(defn demo []
  (let [head-ch (pipeline)]
    (doseq [k (range 10)]
      (go (put! head-ch k)))))

(demo)
```

Produces:

```
A: 0
A: 1
B: 0
A: 2
A: 3
B: 1
C: 0
A: 4
B: 2
A: 5
A: 6
B: 3
C: 1
A: 7
A: 8
B: 4
A: 9
C: 2
B: 5
B: 6
C: 3
B: 7
B: 8
C: 4
B: 9
C: 5
C: 6
C: 7
C: 8
C: 9
```

## License

Copyright © 2013 Michael Drogalis

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
