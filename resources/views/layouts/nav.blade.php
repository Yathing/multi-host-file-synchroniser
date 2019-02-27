@extends('layouts.app')
@section('nav')

@include('layouts.navbar')


                <!-- Page Content -->
        <div id="page-wrapper">
            <div class="container-fluid">
                @yield('content')
            </div>
            <!-- /.container-fluid -->
            <footer class="footer text-center">©2019 Group Project</footer>
        </div>
        <!-- /#page-wrapper -->
        


@endsection