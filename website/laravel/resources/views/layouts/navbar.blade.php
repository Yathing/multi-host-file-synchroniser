<nav class="navbar navbar-default navbar-static-top m-b-0">
    <div class="navbar-header"> <a class="navbar-toggle hidden-sm hidden-md hidden-lg " href="javascript:void(0)" data-toggle="collapse" data-target=".navbar-collapse"><i class="fa fa-bars"></i></a>
        <div class="top-left-part"><a class="logo" href="{{ url('/') }}"><b><img src="" alt="home" /></b><span class="hidden-xs"><img src="" alt="home" /></span></a></div>
        @guest @else
        <ul class="nav navbar-top-links navbar-left m-l-20 hidden-xs">
            <li>
               
            </li>
        </ul>

        <ul class="nav navbar-top-links navbar-right pull-right">

            <li><a class="profile-pic" href="#"><b class="hidden-xs">{{ Auth::user()->name }}</b></a></li>

            <li> <a href="{{ route('logout') }}" onclick="event.preventDefault(); document.getElementById('logout-form').submit();" class="fa fa-sign-out"> 登出 </a>
                <form id="logout-form" action="{{ route('logout') }}" method="POST" style="display: none;">{{ csrf_field() }}</form>
            </li>
        </ul>
        @endguest
    </div>
    <!-- /.navbar-header -->
    <!-- /.navbar-top-links -->
    <!-- /.navbar-static-side -->
</nav>
<!-- Left navbar-header -->
<div class="navbar-default sidebar" role="navigation">
    <div class="sidebar-nav navbar-collapse slimscrollsidebar">
        @guest @else
        <ul class="nav" id="side-menu">
            <li style="padding: 10px 0 0;">
                <a href="{{ url('') }}" class="waves-effect"><i class="fa fa-clock-o fa-fw" aria-hidden="true"></i><span class="hide-menu">Dashboard</span></a>
            </li>
            <li>
                <a href="{{ url('/folders') }}" class="waves-effect"><i class="fa fa-table fa-fw" aria-hidden="true"></i><span class="hide-menu">Folders</span></a>
            </li>
            <li>
                <a href="{{ url('/files') }}" class="waves-effect"><i class="fa fa-globe fa-fw" aria-hidden="true"></i><span class="hide-menu">Files</span></a>
            </li>
            <li>
                <a href="{{ url('') }}" class="waves-effect"><i class="fa fa-font fa-fw" aria-hidden="true"></i><span class="hide-menu">Change Password</span></a>
            </li>
        </ul>
        @endguest
    </div>
</div>
<!-- Left navbar-header end -->
<!-- Page Content -->