# Introduction
Spring-boot and influxdb programming playground.

# grafana
This project uses grafana to present the time series data.
To launch grafana on MacOS, run the command below:

    grafana-server                                        \
        --config=/usr/local/etc/grafana/grafana.ini       \
        --homepath /usr/local/share/grafana               \
        cfg:default.paths.logs=/usr/local/var/log/grafana \
        cfg:default.paths.data=/usr/local/var/lib/grafana \
        cfg:default.paths.plugins=/usr/local/var/lib/grafana/plugins

