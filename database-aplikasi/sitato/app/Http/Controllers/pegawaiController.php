<?php

namespace sitato\Http\Controllers;

use sitato\pegawai;
use Illuminate\Http\Request;

class pegawaiController extends Controller
{
    public function index() 
    {
        $data= pegawai::all();
        return response()->json($data, 200);
    }

    public function login(Request $request) 
    {
        $uname= trim($request->username);
        $pass= trim($request->password);
        $data= pegawai::where('username_pegawai',$uname)->where('sandi_pegawai', $pass)->first(); 
        if(is_null($data))
        {
            return response()->json('Error login', 500);
        }
        else
        {
            return response()->json('Success', 200); 
        }
    }

    public function store(Request $request) //perlu diubah
    {
        $data= new pegawai;
        $data->id_role= $request->id_role;
        $data->id_cabang= $request->id_cabang;
        $data->no_telepon_pegawai= $request->no_telepon_pegawai;
        $data->nama_pegawai= $request->nama_pegawai;
        $data->alamat_pegawai= $request->alamat_pegawai;
        $data->gaji_pegawai= $request->gaji_pegawai;
        $data->username_pegawai= $request->username_pegawai;
        $data->sandi_pegawai= $request->sandi_pegawai;

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

    public function show($id) 
    {
        $data= pegawai::find($id);
        if(is_null($data))
        {
            return response()->json('Not found', 404);
        }
        else
        {
            return response()->json($data, 200);
        }
    }

    public function edit(pegawai $data)
    {
        //
    }

    public function update(Request $request, $id) //perlu diubah
    {
        $data= pegawai::find($id);
        if(!is_null($request->id_role))
        {
            $data->id_role= $request->id_role;
        }
        if(!is_null($request->id_cabang))
        {
            $data->id_cabang= $request->id_cabang;
        }
        if(!is_null($request->no_telepon_pegawai))
        {
            $data->no_telepon_pegawai= $request->no_telepon_pegawai;
        }
        if(!is_null($request->nama_pegawai))
        {
            $data->nama_pegawai= $request->nama_pegawai;
        }
        if(!is_null($request->alamat_pegawai))
        {
            $data->alamat_pegawai= $request->alamat_pegawai;
        }
        if(!is_null($request->gaji_pegawai))
        {
            $data->gaji_pegawai= $request->gaji_pegawai;
        }
        if(!is_null($request->username_pegawai))
        {
            $data->username_pegawai= $request->username_pegawai;
        }
        if(!is_null($request->sandi_pegawai))
        {
            $data->sandi_pegawai= $request->sandi_pegawai;
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

    public function destroy($id)
    {
        $data= pegawai::find($id);
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
