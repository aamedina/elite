# net.wikipunk/elite
https://github.com/EDCD/EDDN

##### Archives and data dumps

Alternatively if you want an archive of past EDDN messages, or a data dump to
use:

- [edgalaxydata](https://edgalaxydata.space/) has various data captures,
    including 'all' (some listener downtime is inevitable) EDDN messages for
    many years.

- [spansh dumps](https://www.spansh.co.uk/dumps) are a "whole galaxy" data set,
    of systems, bodies and stations.  The full `galaxy.json.gz` is **very**
    large, but is currently the only source of an "all known bodies" dump.
    Pay attention to the 'Generated' "time ago" column.

- [EDDB dumps](https://eddb.io/api) represent a snapshot of the data EDDB uses.
    NB: There has been no "bodies" data for years now, EDDB itself stopped
    updating or adding to this.

- [EDSM nightly dumps](https://www.edsm.net/en/nightly-dumps) represent a
    snapshot of the data EDSM uses.  NB: there's only a "last 7 days" bodies
    dump as the full data proved too large to dump in a timely manner.


## Usage

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
