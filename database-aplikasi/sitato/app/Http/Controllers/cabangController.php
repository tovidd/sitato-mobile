<?php

namespace sitato\Http\Controllers;

use sitato\cabang;
use Illuminate\Http\Request;
use DB;

/*
jika keluar pesan error page/session expired 419
coba komen code dibawah di kernel.php
\sitato\Http\Middleware\VerifyCsrfToken::class,
*/

class cabangController extends Controller
{
    /**
     * Display a listing of the resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function index() //done
    {
        $data= cabang::all();
        return response()->json($data, 200);
    }

    /**
     * Show the form for creating a new resource.
     *
     * @return \Illuminate\Http\Response
     */
    public function create()
    {
        //
    }

    /**
     * Store a newly created resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @return \Illuminate\Http\Response
     */
    public function store(Request $request) //done
    {
        $data= new cabang;
        $data->nama_cabang= $request->nama_cabang;
        $data->no_telepon_cabang= $request->no_telepon_cabang;
        $data->alamat_cabang= $request->alamat_cabang;
        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error saving', 500);
        }
        else
        {
            return response()->json('Success', 200);
        }
    }

    /**
     * Display the specified resource.
     *
     * @param  \sitato\cabang  $data
     * @return \Illuminate\Http\Response
     */
    public function show($id) //done
    {
        $data= cabang::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    /**
     * Show the form for editing the specified resource.
     *
     * @param  \sitato\cabang  $data
     * @return \Illuminate\Http\Response
     */
    public function edit(cabang $data)
    {
        //
    }

    /**
     * Update the specified resource in storage.
     *
     * @param  \Illuminate\Http\Request  $request
     * @param  \sitato\cabang  $data
     * @return \Illuminate\Http\Response
     */
    public function update(Request $request, $id) //done
    {
        $data= cabang::find($id);
        if(!is_null($request->nama_cabang))
        {
            $data->nama_cabang= $request->nama_cabang;
        }
        if(!is_null($request->no_telepon_cabang))
        {
            $data->no_telepon_cabang= $request->no_telepon_cabang;
        }
        if(!is_null($request->alamat_cabang))
        {
            $data->alamat_cabang= $request->alamat_cabang;
        }

        $success= $data->save();
        if(!$success)
        {
            return response()->json('Error updating', 500);
        }
        else
        {
            return response()->json('Success updating', 200);
        }
    }

    /**
     * Remove the specified resource from storage.
     *
     * @param  \sitato\cabang  $data
     * @return \Illuminate\Http\Response
     */
    public function destroy($id)
    {
        $data= cabang::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }

        $success= $data->delete();
        if(!$success)
        {
            return response()->json('Error deleting', 500);
        }
        else
        {
            return response()->json('Success deleting', 200);
        }
    }
}
