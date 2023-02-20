# net.wikipunk/elite
An RDF vocabulary for https://github.com/EDCD/EDDN

##### Galactic Data Source

- [spansh dumps](https://www.spansh.co.uk/dumps) are a "whole galaxy" data set,
    of systems, bodies and stations.  The full `galaxy.json.gz` is **very**
    large, but is currently the only source of an "all known bodies" dump.
    Pay attention to the 'Generated' "time ago" column.

## :dev

``` clojure
{:vocab  {:sc/create-fn net.wikipunk.rdf/map->UniversalTranslator
          :init-ns      net.wikipunk.mop.init
          :ns-prefix    "net.wikipunk.rdf."
          :boot         [net.wikipunk.elite.boot/elite]}
 :client {:sc/create-fn datomic.client.api/client
          :server-type  :dev-local
          :system       "dev"}
 :elite  {:sc/create-fn net.wikipunk.datomic/map->Connection
          :sc/refs      [:client]
          :db-name      "elite"}}
```

This is a [schematic](https://github.com/walmartlabs/schematic)
configuration map which is assembled and started using
[component](https://github.com/stuartsierra/component).

``` shell
clojure -A:dev
```

``` clojure
(reset)
```

## License
Copyright (c) 2023 Adrian Medina

Permission to use, copy, modify, and/or distribute this software for
any purpose with or without fee is hereby granted, provided that the
above copyright notice and this permission notice appear in all
copies.

THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL
WARRANTIES WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED
WARRANTIES OF MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE
AUTHOR BE LIABLE FOR ANY SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL
DAMAGES OR ANY DAMAGES WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR
PROFITS, WHETHER IN AN ACTION OF CONTRACT, NEGLIGENCE OR OTHER
TORTIOUS ACTION, ARISING OUT OF OR IN CONNECTION WITH THE USE OR
PERFORMANCE OF THIS SOFTWARE.
