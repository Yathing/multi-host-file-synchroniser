<!DOCTYPE html>
<html lang="en">

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1.0" />
    <title>File Sycn</title>

    <!-- CSS  -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/css/materialize.min.css">
</head>

<body>
    <div class="container">
        
        <form   action="{{ route('logout') }}" method="POST"  >
             @csrf
        <p>Logged as <b>{{ Auth::user()->name }}</b><button type="submit" class="waves-effect waves-light btn">LOGOUT</button ></p>
        </form>

        @isAdmin
        <ul class="collapsible">
            <li>
                <div class="collapsible-header">
                    <i class="material-icons">filter_drama</i> Invitations
                    <span class="new badge">4</span>
                </div>
                <div class="collapsible-body">
                    <p>
                        <span class="red-text"><b>Geo </b></span><a href="">accept</a> | <a href="">deny</a>
                    </p>
                    <p>
                        <span class="red-text"><b>Tom </b></span><a href="">accept</a> | <a href="">deny</a>
                    </p>
                    <p>
                        <span class="red-text"><b>zihao </b></span><a href="">accept</a> | <a href="">deny</a>
                    </p>
                </div>
            </li>
        </ul>
        @endisAdmin
        <h1 class="center-align green-text text-darken-4">File Sync</h1>

 
        @yield('content')



    </div>


    <!--  Scripts-->
    <script src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/materialize/1.0.0/js/materialize.min.js"></script>
    <script>
        var elems = document.querySelectorAll('.collapsible');
        var options;
        var instances = M.Collapsible.init(elems, options);

        var elem2 = document.querySelectorAll('select');
        var instances = M.FormSelect.init(elem2);
    </script>
</body>

</html>