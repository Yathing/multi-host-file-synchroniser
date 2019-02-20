@extends('layouts/nav') 
@section('content')

<form action="{{ route('login') }}" method="post">
    {{ csrf_field() }}
    

    <div class="alert alert-light">
    <h2><i class="fa fa-paw"></i><strong>Hi!</strong> File Sync</h2>
    </div>

    <div class="col-lg-6 col-md-6 col-sm-12 col-xs-12">
            <div class="form-group">
                <label class="col-md-6">Email:</label>
                    <input id="email" type="email" class="form-control" name="email" value="{{ old('email') }}" required autofocus> @if ($errors->has('email'))
                        <span class="help-block">
                                <strong>{{ $errors->first('email') }}</strong>
                        </span> @endif
            </div>

            <div class="form-group">
                <label for="example-email" class="col-md-6">Password:</label>
                    <input id="password" type="password" class="form-control" name="password" required><br> @if ($errors->has('password'))
                        <span class="help-block">
                            <strong>{{ $errors->first('password') }}</strong>
                        </span> @endif
            </div>



            <div class="form-group">
                <button type="submit" class="btn btn-primary">
                    Login
                </button>
            </div>
            <div class="separator">
                <p class="change_link" >
                    <a href="{{ url('/register') }}" class="btn btn-info"> Not Register yet?</a>
                </p>

                <div class="clearfix"></div>
                <br />
            </div>
    </div>
</form>
@endsection