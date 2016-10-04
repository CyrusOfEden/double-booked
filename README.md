# Double Booked

When maintaining a calendar of events, it is important to know if an event overlaps with another event.

Given a sequence of events, each having a start and end time, write a program that will return the sequence of all pairs of overlapping events.

## Usage

```clojure
(require '[double-booked.core :as d])

(d/overlapping-pairs events-seq) ; => returns pairs of overlapping events
```

## Helpers
```clojure
;; Testing helper methods are provided
(require '[double-booked.test-helper :as helper])

(helper/event-stub) ; generates a dynamic event record with a name, start, and end time
(helper/event-stubs 12) ; a list of 12 event-stubs
(helper/prepared-event-stubs) ; a list of 4 dynamic event stubs whose overlapping-pairs solution is known
```

## Testing

```bash
$ git clone https://github.com/knrz/double-booked.git
$ lein deps
$ lein test
```

## License

Copyright © 2016 Kash Nouroozi

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
