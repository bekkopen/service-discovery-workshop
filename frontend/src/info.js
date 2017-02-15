var Info = React.createClass({
    render: function() {
        console.log(this.props.info);
        var labelColor = (this.props.info.jdbcInMemory ? 'label label-warning' : 'label label-info');
        return <div className="row">
                <div className="col-md-3">
                    JDBC URL: <span className={labelColor}>{this.props.info.jdbcUrl}</span>
                </div>
                <div className="col-md-2">
                    Hostname: <span className="label label-info">{this.props.info.hostname}</span>
                </div>
                <div className="col-md-2">
                    Uses tags: <span className="label label-info">
                        {this.props.info.usesTags ? "Yes" : "No"}
                    </span>
                </div>
            </div>;
    }
});

$.ajax("/backend/info")
  .done(function(info) {
        ReactDOM.render(
            <Info info={info} />,
            document.getElementById('info')
        );
  }.bind(this))
  .fail(function(xhr, errorText, thrown) {
        var el = document.getElementById('info');
        el.className = "label label-danger";
        el.textContent = "Error getting info from backend (/backend/info): " + xhr.status + ": " + xhr.statusText;
  }.bind(this));
